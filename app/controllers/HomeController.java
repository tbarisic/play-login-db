package controllers;
import javax.inject.Inject;

import auth.ActionAuthenticator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.User;
import play.data.Form;
import play.data.FormFactory;
import play.data.validation.Constraints;
import play.libs.Json;
import play.mvc.*;
import play.api.db.Database;

import views.html.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static play.data.Form.form;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

    @Inject
    FormFactory formFactory;

    private Database db;

    @Inject
    public HomeController(Database db) {
        this.db = db;
    }
    
    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index() throws SQLException {

        PreparedStatement statement = db.getConnection().prepareStatement("SELECT COUNT(*) FROM pg_stat_activity;");

        String message = "Number of rows: ";
        ResultSet resultSet = statement.executeQuery();

        resultSet.next();
        message += resultSet.getObject(1);

        return ok(index.render(message));
    }

    @Security.Authenticated(ActionAuthenticator.class)
    public Result logout() {
        response().discardCookie(ActionAuthenticator.AUTH_COOKIE);
        return redirect("/");
    }


    @Security.Authenticated(ActionAuthenticator.class)
    public Result secured() {
        return ok(index.render("Ovo je authenticirano"));
    }

    public Result login() {
        JsonNode json = request().body().asJson();
        User user = User.authorize(json.get("username").asText(), json.get("password").asText());
        if (user == null) {
            return Results.unauthorized();
        } else {
            String token = user.createToken();
            response().setCookie(Http.Cookie.builder(ActionAuthenticator.AUTH_COOKIE, token).build());
            return ok(token);
        }
    }

}
