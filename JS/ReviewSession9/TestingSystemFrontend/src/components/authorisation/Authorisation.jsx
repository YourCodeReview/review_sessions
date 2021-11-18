import React from 'react';
import {Box, Button, Card, Chip, Grid, TextField} from "@material-ui/core";
import {API} from "../../api/API";
import {Redirect, useHistory} from "react-router-dom";

const styles = {
    mainForm : {
        position: 'fixed',
        top: '35%',
        left: '45%',
        width: '10%',
        height: '40%',
    },

}

class Authorisation extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            login: '',
            password: '',
            isButtonDisabled: true,
            chipVisible: 'none',
            redirect: null
        }
        this.handleChangeLogin = this.handleChangeLogin.bind(this);
        this.handleChangePassword = this.handleChangePassword.bind(this);
        this.onLoginClick = this.onLoginClick.bind(this);
        this.handleErrorLogin = this.handleErrorLogin.bind(this);
    }
    handleChangeLogin(e) {
        this.setState({login: e.target.value});
    }
    handleChangePassword(e) {
        this.setState({password: e.target.value, isButtonDisabled: this.login === '' });
    }
    onLoginClick() {
        let user = 'username='+this.state.login+'&password='+this.state.password;
        API.loginIn(user).then(
            this.props.history.push('/account')
        ).catch((err) => {
            if(err.response.status === 400)
                this.handleErrorLogin();
        });
    }
    handleErrorLogin() {
        this.setState({login: '', password: '', isButtonDisabled: true, chipVisible: true})
    }

    render() {

        return (
            <Grid container
                  direction={"column"}
                  spacing={2}
                  style={{width: '100%', height: '100%'}}
            >
                <Grid item>
                        <Grid container
                              direction={"column"}
                              style={styles.mainForm}>
                            <Chip label="Incorrect login or password" variant="outlined" style={{display: this.state.chipVisible, color: 'red'}}/>
                            <TextField required label="Login" onChange={this.handleChangeLogin} value={this.state.login}/>
                            <TextField required label="Password" type={"password"} onChange={this.handleChangePassword} value={this.state.password}/>
                            <Button variant={"outlined"} onClick={this.onLoginClick} disabled={this.state.isButtonDisabled}>Sign in</Button>
                        </Grid>
                </Grid>
            </Grid>
        );
    }
}

export default Authorisation;