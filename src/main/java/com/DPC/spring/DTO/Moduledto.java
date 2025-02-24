package com.DPC.spring.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Moduledto {
    private Long id;
    private String nommodule;
    private String matricule;
    private String contenue;
    private String travailafaire;
    private Date date;
}
