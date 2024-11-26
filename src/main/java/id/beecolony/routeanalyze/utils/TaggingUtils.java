package id.beecolony.routeanalyze.utils;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

@Component
public class TaggingUtils {
    
    public String selectedInput(String key,  HashMap<String,Object> optionList, String selectedAtt){
        StringJoiner selected = new StringJoiner("","<select id=\"#attKey\" name=\"#attKey\">","</select>");

        selected.add("<option value=\"\">-Pilih salah satu-</option>");
        for (Map.Entry<String,Object> entry : optionList.entrySet()){
            String option = "<option value=\"#attOpt\" #selected>#valueOpt</option>";

            option = option.replace("#attOpt", (String) entry.getKey());
            option = option.replace("#valueOpt", (String) entry.getValue());
            option = option.replace("#selected", ((String) entry.getKey()).equals(selectedAtt)?"selected":"");

            selected.add(option);
        }

        String select = selected.toString();

        return select.replace("#attKey", key);
    }

    public String selectedInput(String key,  HashMap<String,Object> optionList, String selectedAtt, List<String> styles){
        StringJoiner selected = new StringJoiner("","<select id=\"#attKey\" name=\"#attKey\" #attStyles>","</select>");

        selected.add("<option value=\"\">-Pilih salah satu-</option>");
        for (Map.Entry<String,Object> entry : optionList.entrySet()){
            String option = "<option value=\"#attOpt\" #selected>#valueOpt</option>";

            option = option.replace("#attOpt", (String) entry.getKey());
            option = option.replace("#valueOpt", (String) entry.getValue());
            option = option.replace("#selected", entry.getKey().equals(selectedAtt)?"selected":"");

            selected.add(option);
        }

        String select = selected.toString();

        String style ="";
        if(!styles.isEmpty()){
            style = styles.stream().map(String::valueOf).collect(Collectors.joining(" "));
        }

        return select.replace("#attKey", key)
                    .replace("#attStyles", "class=\""+style+"\"");
    }
}
