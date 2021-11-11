import React from 'react';
import {
    Grid, Button,
    Card, CardActions, CardMedia,
    Typography, TextField, Chip
} from "@material-ui/core";
import AdminImage from '../../img/registration/admin.jpg'
import ProfessorImage from '../../img/registration/professor.jpg'
import StudentImage from '../../img/registration/student.jpg'
import ReactCardFlip from "react-card-flip";
import {API} from "../../api/API";

const minHeightCard = 185;
const minWidthCard = 400;

const styles = {
    adminCardAction: {
        justifyContent: 'center',
        backgroundImage: `url(${AdminImage})`,
        minHeight: minHeightCard,
    },
    professorCardAction: {
        justifyContent: 'center',
        backgroundImage: `url(${ProfessorImage})`,
        minHeight: minHeightCard,
    },
    studentCardAction: {
        justifyContent: 'center',
        backgroundImage: `url(${StudentImage})`,
        minHeight: minHeightCard,
    },
    cardMedia: {
        minHeight: minHeightCard+18,
    },
    errorChip : {

    }
}

class Registration extends React.Component {
    constructor(props) {
        super(props);
        this.state={
            isAdminFlipped: false,
            isProfessorFlipped: false,
            isStudentFlipped: false,
            isButtonDisabled: true,
            isTextFieldError: false,
            itemXs: 4,
            login: '',
            password: '',
            repeatPassword: '',
            userType: '',
            chipVisible: 'none',
        };
        this.flipAdminCard = this.flipAdminCard.bind(this);
        this.flipProfessorCard = this.flipProfessorCard.bind(this);
        this.flipStudentCard = this.flipStudentCard.bind(this);
        this.handleChangeLogin = this.handleChangeLogin.bind(this);
        this.handleChangePassword = this.handleChangePassword.bind(this);
        this.onRegisterClick = this.onRegisterClick.bind(this);
        this.handleChangeRepeatPassword = this.handleChangeRepeatPassword.bind(this);
    };

    flipAdminCard(e) {
        e.preventDefault();
        this.setState(prevState => ({ isAdminFlipped: !prevState.isAdminFlipped, isProfessorFlipped: false, isStudentFlipped: false }));
    }
    flipProfessorCard(e) {
        e.preventDefault();
        this.setState(prevState => ({ isProfessorFlipped: !prevState.isProfessorFlipped, isAdminFlipped: false, isStudentFlipped: false }));
    }
    flipStudentCard(e) {
        e.preventDefault();
        this.setState(prevState => ({ isStudentFlipped: !prevState.isStudentFlipped, isAdminFlipped: false, isProfessorFlipped: false }));
    }
    handleChangeLogin(e) {
        this.setState({login: e.target.value, userType: e.target.id});
    }
    handleChangePassword(e) {
        this.setState({password: e.target.value});
    }
    handleChangeRepeatPassword(e) {
        this.setState({repeatPassword: e.target.value})
        if (this.state.password !== e.target.value) {
            this.setState({isButtonDisabled: true, isTextFieldError: true})
        } else if(this.state.login !== '') {
            this.setState({isButtonDisabled: false, isTextFieldError: false})
        }

    }
    onRegisterClick() {
        let user = {
            login: this.state.login,
            password: this.state.password,
            authority: this.state.userType
        }
        API.postNewUser(JSON.stringify(user))
            .then(() => {
                    user = "username=" + this.state.login + "&password=" + this.state.password;
                    API.loginIn(user).then(this.props.history.push('/account'));
                }
            )
            .catch((err) => {
                if(err.response.status === 400) {
                    this.setState({
                        login: '',
                        password: '',
                        repeatPassword: '',
                        chipVisible: true
                    })
                }
            });

    }

        render() {

            return (
                <Grid
                    container
                    direction={"column"}
                    spacing={3}
                >
                    {/*> Administrator card pair*/}
                    <Grid item>
                        <Grid
                            container
                            spacing={3}
                            direction={"row-reverse"}
                            alignItems={"center"}
                            justifyContent={"center"}
                        >
                            <Grid item xs={this.state.itemXs}>
                                <ReactCardFlip isFlipped={this.state.isAdminFlipped}>
                                    <Card>
                                        <CardActions style={styles.adminCardAction}>
                                            <Button variant={"outlined"} onClick={this.flipAdminCard}>Administrator</Button>
                                        </CardActions>
                                    </Card>
                                    <Card>
                                        <Grid
                                            container
                                            direction={"column"}>
                                            <TextField required label="Login" onChange={this.handleChangeLogin} id={"Administrator"} value={this.state.login}/>
                                            <TextField required label="Password" type={"password"} onChange={this.handleChangePassword} value={this.state.password}/>
                                            <TextField required
                                                       error={this.state.isTextFieldError}
                                                       label={"Repeat password"}
                                                       type={"password"}
                                                       value={this.state.repeatPassword}
                                                       onChange={this.handleChangeRepeatPassword}/>
                                            <Button disabled={this.state.isButtonDisabled} variant={"outlined"} onClick={this.onRegisterClick}>Register</Button>
                                        </Grid>
                                    </Card>
                                </ReactCardFlip>
                            </Grid>
                            <Grid item xs={this.state.itemXs}>
                                <Card>
                                    <CardMedia image={AdminImage} style={styles.cardMedia}/>
                                </Card>
                            </Grid>
                        </Grid>
                    </Grid>

                    {/*> Professor card pair*/}
                    <Grid item>
                        <Grid
                            container
                            spacing={3}
                            direction={"row"}
                            alignItems={"center"}
                            justify={"center"}
                        >
                            <Grid item xs={this.state.itemXs}>
                                <ReactCardFlip isFlipped={this.state.isProfessorFlipped}>
                                    <Card>
                                        <CardActions style={styles.professorCardAction}>
                                            <Button variant={"outlined"} onClick={this.flipProfessorCard}>Professor</Button>
                                        </CardActions>
                                    </Card>
                                    <Card>
                                        <Grid container
                                              direction={"column"}>
                                            <TextField required label="Login" onChange={this.handleChangeLogin} id={"Professor"} value={this.state.login}/>
                                            <TextField required label="Password" type={"password"} onChange={this.handleChangePassword} value={this.state.password}/>
                                            <TextField required
                                                       error={this.state.isTextFieldError}
                                                       label={"Repeat password"}
                                                       type={"password"}
                                                       value={this.state.repeatPassword}
                                                       onChange={this.handleChangeRepeatPassword}/>
                                            <Button disabled={this.state.isButtonDisabled} variant={"outlined"} onClick={this.onRegisterClick}>Register</Button>
                                        </Grid>
                                    </Card>
                                </ReactCardFlip>
                            </Grid>
                            <Grid item xs={this.state.itemXs}>
                                <Card>
                                    <CardMedia image={ProfessorImage} style={styles.cardMedia}/>
                                </Card>
                            </Grid>
                        </Grid>
                        </Grid>

                        {/*> Student card pair*/}
                        <Grid item>
                        <Grid
                            container
                            spacing={3}
                            direction={"row-reverse"}
                            alignItems={"center"}
                            justify={"center"}
                        >
                            <Grid item xs={this.state.itemXs}>
                                <ReactCardFlip isFlipped={this.state.isStudentFlipped}>
                                    <Card>
                                        <CardActions style={styles.studentCardAction}>
                                            <Button variant={"outlined"} onClick={this.flipStudentCard}>Student</Button>
                                        </CardActions>
                                    </Card>
                                    <Card>
                                        <Grid container
                                              direction={"column"}>
                                            <TextField required label="Login" onChange={this.handleChangeLogin} id={"Student"} value={this.state.login}/>
                                            <TextField required label="Password" type={"password"} onChange={this.handleChangePassword} value={this.state.password}/>
                                            <TextField required
                                                       error={this.state.isTextFieldError}
                                                       label={"Repeat password"}
                                                       type={"password"}
                                                       value={this.state.repeatPassword}
                                                       onChange={this.handleChangeRepeatPassword}/>
                                            <Button disabled={this.state.isButtonDisabled} variant={"outlined"} onClick={this.onRegisterClick}>Register</Button>
                                        </Grid>
                                    </Card>
                                </ReactCardFlip>
                            </Grid>
                            <Grid item xs={this.state.itemXs}>
                                <Card>
                                    <CardMedia image={StudentImage} style={styles.cardMedia}/>
                                </Card>
                            </Grid>
                        </Grid>

                    </Grid>
                    <Chip label="User with this login already exists!"
                          variant="outlined"
                          style={{display: this.state.chipVisible, color: '#C02402',  width: '20%', top: '50%', left: '40%', position: 'relative'}}
                    />
                </Grid>
            );
        }
}
export default Registration;