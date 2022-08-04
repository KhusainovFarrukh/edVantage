package kh.farrukh.edvantage.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TokenBody {
    private String email;
    private List<String> features;
}
