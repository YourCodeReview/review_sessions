import 'package:flutter/material.dart';
import 'package:flutter_gen/gen_l10n/app_localizations.dart';
import 'package:role_heroes/clients/api/exceptions/server_error.dart';
import 'package:role_heroes/components/main_snackbar.dart';
import 'package:role_heroes/constants.dart';
import 'package:role_heroes/controllers/auth.dart';
import 'package:role_heroes/screens/game_list.dart';
import 'package:role_heroes/screens/register.dart';
import 'package:role_heroes/utils/builders/error_notification_builder.dart';
import 'package:role_heroes/widgets/pre_loader.dart';

class LoginFormValues {
  String login;
  String password;
}

class LoginScreen extends StatefulWidget {
  static final routeName = '/login';

  final IAuthController controller;

  const LoginScreen({
    Key key,
    @required this.controller,
  }) : super(key: key);

  @override
  State<StatefulWidget> createState() {
    return _LoginScreen();
  }
}

class _LoginScreen extends State<LoginScreen> {
  final _formKey = GlobalKey<FormState>();
  final LoginFormValues _formValues = LoginFormValues();
  final IErrorNotificationBuilder errorNotificationBuilder = ErrorNotificationBuilder();

  void login(BuildContext context) {
    PreLoader.show(context);

    widget
      .controller
      .login(_formValues.login, _formValues.password)
      .then((result) {
        PreLoader.hide(context);

        if (result is bool && result) {
          Navigator.of(context).pushReplacementNamed(GameScreen.routeName);
        } else {
          ScaffoldMessenger.of(context).showSnackBar(MainSnackBar(
            duration: Duration(seconds: 10),
            content: Text(AppLocalizations.of(context).log_in_fail),
          ));
        }
      })
      .catchError((error) {
        PreLoader.hide(context);

        if (error.runtimeType == ServerError) {
          errorNotificationBuilder.rest();
          errorNotificationBuilder.build(error);
          ScaffoldMessenger.of(context).showSnackBar(
            errorNotificationBuilder.getResult()
          );
        } else {
          ScaffoldMessenger.of(context).showSnackBar(
            MainSnackBar(
              content: Text(AppLocalizations.of(context).log_in_fail),
            )
          );
        }
      });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Container(
        height: 500.0,
        padding: const EdgeInsets.all(gDefaultPadding),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.spaceAround,
          children: [
            Padding(
              padding: EdgeInsets.only(top: 20.0),
              child: Image(
                width: 100.0,
                image: AssetImage('assets/images/logo.png'),
              ),
            ),
            Row(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                Text(
                  AppLocalizations.of(context).logo_first_word,
                  style: TextStyle(
                    color: gTextColor,
                    fontSize: 28.0,
                    fontWeight: FontWeight.bold,
                  ),
                ),
                Text(
                  AppLocalizations.of(context).logo_second_word,
                  style: TextStyle(
                    color: gTextLightColor,
                    fontSize: 28.0,
                  ),
                ),
              ],
            ),
            Form(
              key: _formKey,
              child: Column(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  Container(
                    margin: const EdgeInsets.only(bottom: gDefaultMargin),
                    child: TextFormField(
                      textInputAction: TextInputAction.go,
                      keyboardType: TextInputType.text,
                      validator: (String value) {
                        String result;
                        if (value.length < 3) {
                          result = AppLocalizations.of(context).validation_size(
                            AppLocalizations.of(context).login,
                            AppLocalizations.of(context).size('more'),
                            3,
                            AppLocalizations.of(context).characters,
                          );
                        }
                        return result;
                      },
                      onSaved: (String value) => _formValues.login = value,
                      decoration: InputDecoration(
                        labelText: AppLocalizations.of(context).login,
                        hintText: AppLocalizations.of(context).placeholder(
                          AppLocalizations.of(context).login
                        ),
                        labelStyle: Theme.of(context).textTheme.bodyText2,
                      ),
                    ),
                  ),
                  Container(
                    margin: const EdgeInsets.only(bottom: gDefaultMargin),
                    child: TextFormField(
                      textInputAction: TextInputAction.done,
                      obscureText: true,
                      keyboardType: TextInputType.text,
                      validator: (String value) {
                        String result;
                        if (value.length < 6) {
                          result = AppLocalizations.of(context).validation_size(
                            AppLocalizations.of(context).password,
                            AppLocalizations.of(context).size('more'),
                            6,
                            AppLocalizations.of(context).characters,
                          );
                        }
                        return result;
                      },
                      onSaved: (String value) => _formValues.password = value,
                      decoration: InputDecoration(
                        labelText: AppLocalizations.of(context).password,
                        hintText: AppLocalizations.of(context).placeholder(
                          AppLocalizations.of(context).password
                        ),
                        labelStyle: Theme.of(context).textTheme.bodyText2,
                      ),
                    ),
                  ),
                  Row(
                    mainAxisAlignment: MainAxisAlignment.spaceAround,
                    children: [
                      ElevatedButton(
                        onPressed: () {
                          _formKey.currentState.save();
                          if (_formKey.currentState.validate())
                            this.login(context);
                        },
                        child: Text(AppLocalizations.of(context).log_in),
                      ),
                      ElevatedButton(
                        style: ElevatedButton.styleFrom(primary: Colors.grey),
                        onPressed: () => Navigator.of(context).pushNamed(RegisterScreen.routeName),
                        child: Text(AppLocalizations.of(context).register),
                      ),
                    ],
                  ),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }
}
