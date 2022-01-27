import 'package:flutter/material.dart';
import 'package:flutter_gen/gen_l10n/app_localizations.dart';
import 'package:role_heroes/clients/api/exceptions/server_error.dart';
import 'package:role_heroes/components/main_snackbar.dart';
import 'package:role_heroes/constants.dart';
import 'package:role_heroes/controllers/auth.dart';
import 'package:role_heroes/screens/game_list.dart';
import 'package:role_heroes/utils/builders/error_notification_builder.dart';
import 'package:role_heroes/widgets/pre_loader.dart';

class RegisterFormValues {
  String login;
  String password;
  String passwordConfirmation;
}

class RegisterScreen extends StatelessWidget {
  static final routeName = '/register';

  final double heightContainerInput = 80.0;

  final GlobalKey<FormState> _formKey = GlobalKey<FormState>();
  final RegisterFormValues _formValues = RegisterFormValues();
  // TODO заменить на что-то внятное
  final IErrorNotificationBuilder errorNotificationBuilder = ErrorNotificationBuilder();

  final IAuthController controller;

  RegisterScreen({
    Key key,
    @required this.controller,
  }) : super(key: key);

  void register(BuildContext context) async {
    PreLoader.show(context);

    controller.register(
        _formValues.login,
        _formValues.password,
        _formValues.passwordConfirmation,
    ).then((result) {
      PreLoader.hide(context);
      SnackBar snackBar;

      if (result) {
        snackBar = MainSnackBar(
          content: Text(AppLocalizations.of(context).register_success),
          duration: Duration(seconds: 1),
          onVisible: () {
            Navigator.of(context).pushReplacementNamed(GameScreen.routeName);
          },
        );
      } else {
        snackBar = MainSnackBar(content: Text(AppLocalizations.of(context).register_fail));
      }
      ScaffoldMessenger.of(context).showSnackBar(snackBar);
    }).catchError((error) {
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
            content: Text(AppLocalizations.of(context).register_fail),
          )
        );
      }
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Container(
        height: 600.0,
        padding: const EdgeInsets.all(gDefaultPadding),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          crossAxisAlignment: CrossAxisAlignment.center,
          children: [
            Container(
              margin: const EdgeInsets.only(bottom: gDefaultPadding),
              child: Text(
                AppLocalizations.of(context).register_form,
                style: Theme.of(context).textTheme.headline2,
              ),
            ),
            Form(
              key: _formKey,
              child: Column(
                mainAxisAlignment: MainAxisAlignment.spaceAround,
                children: [
                  Container(
                    height: heightContainerInput,
                    child: TextFormField(
                      keyboardType: TextInputType.text,
                      textInputAction: TextInputAction.next,
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
                      onSaved: (value) => _formValues.login = value,
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
                    height: heightContainerInput,
                    child: TextFormField(
                      obscureText: true,
                      textInputAction: TextInputAction.next,
                      keyboardType: TextInputType.visiblePassword,
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
                      onSaved: (value) => _formValues.password = value,
                      decoration: InputDecoration(
                        labelText: AppLocalizations.of(context).password,
                        hintText: AppLocalizations.of(context).placeholder(
                          AppLocalizations.of(context).password
                        ),
                        labelStyle: Theme.of(context).textTheme.bodyText2,
                      ),
                    ),
                  ),
                  Container(
                    height: heightContainerInput,
                    margin: const EdgeInsets.only(bottom: gDefaultMargin),
                    child: TextFormField(
                      obscureText: true,
                      textInputAction: TextInputAction.done,
                      keyboardType: TextInputType.visiblePassword,
                      validator: (String value) {
                        String result;
                        if (value.length < 6) {
                          result = AppLocalizations.of(context).validation_size(
                            AppLocalizations.of(context).password_confirmation,
                            AppLocalizations.of(context).size('more'),
                            6,
                            AppLocalizations.of(context).characters,
                          );
                        }
                        if (value != _formValues.password) {
                          result = AppLocalizations.of(context).validation_equals(
                            AppLocalizations.of(context).password_confirmation,
                            AppLocalizations.of(context).password,
                          );
                        }
                        return result;
                      },
                      onSaved: (value) => _formValues.passwordConfirmation = value,
                      decoration: InputDecoration(
                        labelText: AppLocalizations.of(context).password_confirmation,
                        hintText: AppLocalizations.of(context).placeholder(
                          AppLocalizations.of(context).password_confirmation
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
                            register(context);
                        },
                        child: Text(AppLocalizations.of(context).to_register),
                      ),
                      ElevatedButton(
                        // TODO create style
                        style: ElevatedButton.styleFrom(
                          primary: Colors.grey,
                        ),
                        onPressed: Navigator.of(context).pop,
                        child: Text(AppLocalizations.of(context).log_in),
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
