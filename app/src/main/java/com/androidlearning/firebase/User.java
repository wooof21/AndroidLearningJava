package com.androidlearning.firebase;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * To use custom object in Firebase Realtime DB
 *
 * the object requires the NoArgsConstructor'
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    private String name;
    private String email;
}
