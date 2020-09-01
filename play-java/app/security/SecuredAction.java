package security;

import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * Created by Chester on 23.02.2017.
 */
public class SecuredAction extends Action.Simple {
    @Override
    public CompletionStage<Result> call(Http.Context context){
        String login = context.session().get("login");
        return (login!=null)?(delegate.call(context)):(CompletableFuture.completedFuture(unauthorized()));
    }
}
