package com.androidlearning.firebase.journal.model.pojo;

import com.google.firebase.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Journal {

    private String title;
    private String thoughts;
    private String imgUrl;
    private String userId;
    private String username;
    private Timestamp timeAdded;
}
