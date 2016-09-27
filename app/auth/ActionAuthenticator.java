package auth;

import models.User;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;

public class ActionAuthenticator extends Security.Authenticator {

    public static String AUTH_COOKIE = "PLAY_AUTH_COOKIE";

    @Override
    public String getUsername(Http.Context ctx) {
        Http.Cookie authCookie =  ctx.request().cookie(AUTH_COOKIE);
        if (authCookie != null) {
            String token = ctx.request().cookie(AUTH_COOKIE).value();
            System.out.println(token);
            if (token != null) {
                //autoriziraj token i ak je ok vrati username tog usera ak je ok, ak nije vrati null
                return token;
            }
        }

        return null;
    }

    @Override
    public Result onUnauthorized(Http.Context ctx) {
        return unauthorized();
    }
}