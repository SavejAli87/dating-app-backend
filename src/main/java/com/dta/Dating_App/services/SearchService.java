package com.dta.Dating_App.services;

import com.dta.Dating_App.DTO.SearchFilterRequest;
import com.dta.Dating_App.entitys.User;
import com.dta.Dating_App.entitys.UserProfile;
import com.dta.Dating_App.repository.UserProfileRepository;
import com.dta.Dating_App.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;

    public List<User> search(SearchFilterRequest request){

        //  Step 1: fetch users (same as before)
        List<User> users = userRepository.findAll(); //  replace custom query later

        //  Step 2: filter using profile
        List<User> filteredUsers = users.stream()
                .filter(user -> {
                    UserProfile profile = userProfileRepository.findByUserId(user.getId()).orElse(null);
                    if (profile == null) return false;

                    // age filter
                    if (request.getMinAge() != null && profile.getAge() != null &&
                            profile.getAge() < request.getMinAge()) return false;

                    if (request.getMaxAge() != null && profile.getAge() != null &&
                            profile.getAge() > request.getMaxAge()) return false;

                    // language
                    if (request.getLanguage() != null &&
                            !request.getLanguage().equalsIgnoreCase(profile.getLanguage()))
                        return false;

                    // ethnicity
                    if (request.getEthnicity() != null &&
                            !request.getEthnicity().equalsIgnoreCase(profile.getEthnicity()))
                        return false;

                    // smoke
                    if (request.getSmoke() != null &&
                            !request.getSmoke().equalsIgnoreCase(profile.getSmoke()))
                        return false;

                    // drink
                    if (request.getDrink() != null &&
                            !request.getDrink().equalsIgnoreCase(profile.getDrink()))
                        return false;

                    return true;
                })
                .toList();

        //  Step 3: sorting (based on profile age)
        if ("ageAsc".equalsIgnoreCase(request.getSortBy())){
            filteredUsers.sort(Comparator.comparingInt(user -> {
                UserProfile profile = userProfileRepository.findByUserId(user.getId()).orElse(null);
                return profile != null && profile.getAge() != null ? profile.getAge() : 0;
            }));
        }
        else if ("ageDesc".equalsIgnoreCase(request.getSortBy())){
            filteredUsers.sort((a, b) -> {
                UserProfile p1 = userProfileRepository.findByUserId(a.getId()).orElse(null);
                UserProfile p2 = userProfileRepository.findByUserId(b.getId()).orElse(null);

                int age1 = (p1 != null && p1.getAge() != null) ? p1.getAge() : 0;
                int age2 = (p2 != null && p2.getAge() != null) ? p2.getAge() : 0;

                return Integer.compare(age2, age1);
            });
        }

        return filteredUsers;
    }
}