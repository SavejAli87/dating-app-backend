package com.dta.Dating_App.services;

import com.dta.Dating_App.DTO.UserFilterRequest;
import com.dta.Dating_App.entitys.User;
import com.dta.Dating_App.repository.UserRepository;
import com.dta.Dating_App.specification.UserSpecifications;
import com.dta.Dating_App.utils.DistanceUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserFilterService {

    private final UserRepository userRepository;

    public Page<User> filterUsers(UserFilterRequest request) {

        Pageable pageable = PageRequest.of(
                request.getPage(),
                request.getSize(),
                Sort.by(Sort.Direction.DESC, "online") // default online first
        );

        Page<User> users = userRepository.findAll(UserSpecifications.filterUsers(request), pageable);

        // distance filter (Java side)
        if (request.getWorldwide() != null && request.getWorldwide()) {
            return users;
        }

        if (request.getMaxDistanceKm() != null && request.getUserId() != null) {

            User me = userRepository.findById(request.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            if (me.getCurrentLat() == null || me.getCurrentLng() == null) {
                throw new RuntimeException("Your location not set");
            }

            List<User> filtered = users.getContent().stream()
                    .filter(u -> u.getCurrentLat() != null && u.getCurrentLng() != null)
                    .filter(u -> DistanceUtil.distanceKm(
                            me.getCurrentLat(), me.getCurrentLng(),
                            u.getCurrentLat(), u.getCurrentLng()
                    ) <= request.getMaxDistanceKm())
                    .collect(Collectors.toList());

            return new PageImpl<>(filtered, pageable, filtered.size());
        }

        return users;
    }
}
