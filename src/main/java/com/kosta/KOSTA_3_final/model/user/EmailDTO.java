package com.kosta.KOSTA_3_final.model.user;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EmailDTO {
    private String address;
    private String title;
    private String message;
}