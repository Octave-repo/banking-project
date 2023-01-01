package fr.banking.services.dto.compte;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostCardRequest {
    String titulaireCarte;
    int code;
}
