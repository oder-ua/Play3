package security;

import play.mvc.Http;
import play.mvc.Security;

/**
 * Created by Chester on 23.02.2017.
 */
public class Secured extends Security.Authenticator {

    @Override
    public String getUsername(Http.Context context){
        return context.session().get("login");
    }
}
