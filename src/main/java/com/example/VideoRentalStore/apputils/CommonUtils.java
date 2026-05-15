package com.example.VideoRentalStore.apputils;

import org.springframework.data.domain.Sort;

public class CommonUtils {

    public static Sort buildSort(String sortBy, String sortOrder) {

       return sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

    }

}
