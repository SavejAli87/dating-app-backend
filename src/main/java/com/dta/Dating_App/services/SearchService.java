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
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;

    public List<User> search(SearchFilterRequest request){

        //  Step 1: fetch all users
        List<User> users = userRepository.findAll();

        //  Step 2: preload all profiles (optimization 🔥)
        Map<Long, UserProfile> profileMap = userProfileRepository.findAll()
                .stream()
                .collect(Collectors.toMap(
                        p -> p.getUser().getId(),
                        p -> p
                ));

        //  Step 3: filtering
        List<User> filteredUsers = users.stream()
                .filter(user -> {

                    UserProfile profile = profileMap.get(user.getId());
                    if (profile == null) return false;


                    // 🔹 Gender filter (FIXED)
                    if (request.getGender() != null && !request.getGender().isBlank()) {

                        String reqGender = request.getGender().trim().toLowerCase();

                        String profileGender = profile.getGender() != null
                                ? profile.getGender().trim().toLowerCase()
                                : "";

                        if (!reqGender.equals(profileGender)) {
                            return false;
                        }
                    }
                    // 🔹 Name filter (partial match)
                    if (request.getName() != null &&
                            user.getName() != null &&
                            !user.getName().toLowerCase().contains(request.getName().toLowerCase()))
                        return false;

                    // 🔹 Age filter
                    if (request.getMinAge() != null &&
                            profile.getAge() != null &&
                            profile.getAge() < request.getMinAge())
                        return false;

                    if (request.getMaxAge() != null &&
                            profile.getAge() != null &&
                            profile.getAge() > request.getMaxAge())
                        return false;

                    // 🔹 Language
                    if (request.getLanguage() != null &&
                            profile.getLanguage() != null &&
                            !request.getLanguage().equalsIgnoreCase(profile.getLanguage()))
                        return false;

                    // 🔹 Ethnicity
                    if (request.getEthnicity() != null &&
                            profile.getEthnicity() != null &&
                            !request.getEthnicity().equalsIgnoreCase(profile.getEthnicity()))
                        return false;

                    // 🔹 Smoke
                    if (request.getSmoke() != null &&
                            profile.getSmoke() != null &&
                            !request.getSmoke().equalsIgnoreCase(profile.getSmoke()))
                        return false;

                    // 🔹 Drink
                    if (request.getDrink() != null &&
                            profile.getDrink() != null &&
                            !request.getDrink().equalsIgnoreCase(profile.getDrink()))
                        return false;

                    return true;
                })
                .toList();

        //  Step 4: sorting (age)
        if ("ageAsc".equalsIgnoreCase(request.getSortBy())){
            filteredUsers.sort(Comparator.comparingInt(user -> {
                UserProfile p = profileMap.get(user.getId());
                return (p != null && p.getAge() != null) ? p.getAge() : 0;
            }));
        }
        else if ("ageDesc".equalsIgnoreCase(request.getSortBy())){
            filteredUsers.sort((a, b) -> {
                UserProfile p1 = profileMap.get(a.getId());
                UserProfile p2 = profileMap.get(b.getId());

                int age1 = (p1 != null && p1.getAge() != null) ? p1.getAge() : 0;
                int age2 = (p2 != null && p2.getAge() != null) ? p2.getAge() : 0;

                return Integer.compare(age2, age1);
            });
        }

        return filteredUsers;
    }
}