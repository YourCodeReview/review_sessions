import React from 'react';

import {Route, Switch, Redirect, withRouter} from "react-router-dom";
import MainPage from "../mainPage/MainPage";
import Registration from "../registration/Registration";
import Authorisation from "../authorisation/Authorisation";
import Account from "../lk/Account";

const App = (props) => {
    const { history } = props

    return (
          <Switch history={history}>
              <Route path='/home' component={MainPage} />
              <Route path='/registration' component={Registration} />
              <Route path={'/authorisation'} component={Authorisation} />
              <Route path={'/account'} component={Account} />
            <Redirect from='/' to='/home'/>
          </Switch>
    );
}

export default withRouter(App);
