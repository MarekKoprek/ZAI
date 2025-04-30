package com.example.fitshop.converter;

import com.example.fitshop.dto.OpinionDTO;
import com.example.fitshop.model.Opinion;
import org.springframework.stereotype.Component;

@Component
public class OpinionToOpinionDTO {
    public OpinionDTO convert(Opinion opinion){
        return new OpinionDTO(opinion.getRating());
    }
}
