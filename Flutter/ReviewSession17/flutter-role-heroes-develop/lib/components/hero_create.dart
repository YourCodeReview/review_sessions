import 'package:flutter/material.dart';
import 'package:flutter_gen/gen_l10n/app_localizations.dart';
import 'package:role_heroes/clients/api/exceptions/server_error.dart';
import 'package:role_heroes/components/main_snackbar.dart';
import 'package:role_heroes/components/pre_loader_widget.dart';
import 'package:role_heroes/constants.dart';
import 'package:role_heroes/controllers/user_hero.dart';
import 'package:role_heroes/modules/games/models/game.dart';
import 'package:role_heroes/widgets/pre_loader.dart';

class HeroCreateFormValues {
  int gameId;
  String name;
}

class HeroCreateForm extends StatefulWidget {
  final IUserHeroController controller;

  HeroCreateForm({
    Key key,
    @required this.controller,
  }) : super(key: key);

  @override
  _HeroCreateFormState createState() => _HeroCreateFormState();
}

class _HeroCreateFormState extends State<HeroCreateForm> {
  final _formKey = GlobalKey<FormState>();
  final HeroCreateFormValues _formValues = HeroCreateFormValues();

  void createHero(BuildContext context) async {
    PreLoader.show(context);

    widget.controller.create(_formValues.gameId, _formValues.name)
      .then((value) {
        PreLoader.hide(context);

        ScaffoldMessenger.of(context).showSnackBar(MainSnackBar(
            content: Text(
              AppLocalizations.of(context).hero_create_success,
            ),
          )
        );

        Navigator.of(context).pop();
      })
      .catchError((error) {
        PreLoader.hide(context);

        SnackBar snackBar = MainSnackBar(
          content: Text(AppLocalizations.of(context).service_error),
        );

        if (error.runtimeType == ServerError) {
          snackBar = ServerError.toSnackBar(error);
        }

        ScaffoldMessenger.of(context).showSnackBar(snackBar);
    });
  }

  @override
  Widget build(BuildContext context) {
    Map<String, dynamic> defaultValues = ModalRoute.of(context).settings.arguments as Map<String, dynamic>;
    _formValues.gameId = defaultValues['gameId'];

    return Container(
      padding: EdgeInsets.all(gDefaultPadding),
      child: Form(
        key: _formKey,
        child: Column(
          children: [
            Container(
              margin: EdgeInsets.only(bottom: gDefaultMargin),
              alignment: AlignmentDirectional.topStart,
              child: FutureBuilder(
                future: widget.controller.gamesForCreateUserHero(),
                builder: (BuildContext context, AsyncSnapshot<List<Game>> snapshot) {
                  Widget result = Center(
                    child: PreLoaderWidget(),
                  );
                  if (snapshot.hasData) {
                    result = DropdownButton<int>(
                      hint: Text(AppLocalizations.of(context).hero_create_choose_game),
                      items: snapshot.data.map<DropdownMenuItem<int>>((Game game) {
                        return DropdownMenuItem<int>(value: game.id, child: Text(game.name));
                      }).toList(),
                      onChanged: (int gameId) { _formValues.gameId = gameId; },
                      value: defaultValues['gameId'],
                    );
                  }
                  else if (snapshot.hasError) {
                    result = Text(snapshot.error.toString());
                  }
                  return result;
                },
              ),
            ),
            Container(
              margin: EdgeInsets.only(bottom: gDefaultMargin),
              child: TextFormField(
                keyboardType: TextInputType.text,
                onSaved: (String value) { _formValues.name = value; },
                decoration: InputDecoration(
                  labelText: AppLocalizations.of(context).hero_detail_main_fields_name,
                  hintText: AppLocalizations.of(context).placeholder(
                    AppLocalizations.of(context).hero_detail_main_fields_name
                  ),
                  labelStyle: Theme.of(context).textTheme.bodyText2,
                ),
              ),
            ),
            Container(
              alignment: Alignment.topLeft,
              child: ElevatedButton(
                child: Text(AppLocalizations.of(context).hero_create),
                onPressed: () {
                  _formKey.currentState.save();
                  if (_formKey.currentState.validate())
                    createHero(context);
                },
              ),
            ),
          ],
        ),
      ),
    );
  }
}
