package com.dta.Dating_App.services;

import com.dta.Dating_App.DTO.SearchFilterRequest;
import com.dta.Dating_App.entitys.User;
import com.dta.Dating_App.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final UserRepository userRepository;

    public List<User> search(SearchFilterRequest request){
        List<User> users = userRepository.searchUsers(
                request.getMinAge(),
                request.getMaxAge(),
                request.getLanguage(),
                request.getEthnicity(),
                request.getSmoke(),
                request.getDrink()
        );

        // Sorting
        if ("ageAsc".equalsIgnoreCase(request.getSortBy())){
            users.sort(Comparator.comparingInt(User::getAge));
        } else if ("ageDesc".equalsIgnoreCase(request.getSortBy())) {
            users.sort((a,b) -> Integer.compare(b.getAge(),a.getAge()));
        }
        return users;
    }

}
