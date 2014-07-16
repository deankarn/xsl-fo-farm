/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cwc.util.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author karnd01
 */
public final class Validate {

    public static Boolean isValidEmail( String email ){

//        String emailRegEx = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        // Compile and get a reference to a Pattern object.
//        Pattern pattern = Pattern.compile("^\\-*[a-z][a-z|0-9|\\-]*([_][a-z|0-9|\\-]+)*([.][a-z|0-9|\\-]+([_][a-z|0-9|\\-]+)*\\-*)?@\\-*[a-z][a-z|0-9|\\-]*\\.([a-z][a-z|0-9]*(\\.[a-z][a-z|0-9]*)?)$");
        Pattern pattern = Pattern.compile("^\\-*[a-zA-Z][a-zA-Z|0-9|\\-]*([_][a-zA-Z|0-9|\\-]+)*([.][a-zA-Z|0-9|\\-]+([_][a-zA-Z|0-9|\\-]+)*\\-*)?@\\-*[a-zA-Z][a-zA-Z|0-9|\\-]*\\.([a-zA-Z][a-zA-Z|0-9]*(\\.[a-zA-Z][a-zA-Z|0-9]*)?)$");
        // Get a matcher object - we cover this next.
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }
}
