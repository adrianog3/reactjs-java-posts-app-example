import { Switch, Route } from "react-router-dom";

import Home from "./pages/Home";
import CreatePost from "./pages/CreatePost";

function Routes() {
  return (
    <Switch>
      <Route path="/" exact component={Home} />
      <Route path="/create-post" component={CreatePost} />
    </Switch>
  );
}

export default Routes;
