import 'package:flutter/material.dart';
import 'package:flutter_gen/gen_l10n/app_localizations.dart';
import 'package:role_heroes/clients/api/exceptions/server_error.dart';
import 'package:role_heroes/components/main_snackbar.dart';
import 'package:role_heroes/components/pre_loader_widget.dart';
import 'package:role_heroes/controllers/structural_attribute.dart';
import 'package:role_heroes/modules/heroes/models/user_hero.dart';
import 'package:role_heroes/modules/structural_attribute/models/structural_attribute.dart';
import 'package:role_heroes/modules/structural_attribute/models/structural_value.dart';
import 'package:role_heroes/modules/structural_attribute/widgets/structural_value_detail.dart';
import 'package:role_heroes/widgets/pre_loader.dart';

class StructuralAttributeValuesSelect extends StatefulWidget {
  final UserHero hero;
  final StructuralAttribute attribute;
  final IStructuralAttributeController controller;

  StructuralAttributeValuesSelect({
    Key key,
    @required this.hero,
    @required this.attribute,
    @required this.controller,
  }) : super(key: key);

  @override
  _StructuralAttributeValuesSelectState createState() => _StructuralAttributeValuesSelectState();
}

class _StructuralAttributeValuesSelectState extends State<StructuralAttributeValuesSelect> {
  final TextEditingController controller = new TextEditingController();
  String _query = '';

  List<DataRow> _getDataRowsFromValues(List<StructuralValue> values) {
    values = values.where((value) {
      final String query = _query.toLowerCase();
      final bool queryContainToDescription = value.description == null && query == ''
        || value.description != null && value.description.toLowerCase().contains(query);
      return value.name.toLowerCase().contains(query)
          || queryContainToDescription;
    }).toList();

    return values.map((StructuralValue value) {
      return DataRow(
        cells: [
          DataCell(
            Text(value.name),
            onTap: () {
              showDialog(
                context: context,
                builder: (BuildContext context) => StructuralValueDetail(
                  fields: widget.attribute.fields,
                  value: value,
                ),
              );
            },
          ),
        ],
        selected: widget.attribute.values.contains(value),
        onSelectChanged: (bool selected) {
          setState(() {
            widget.attribute.selectedValue(value);
          });
        },
      );
    }).toList();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(AppLocalizations.of(context).service_error),
        actions: [
          IconButton(
            onPressed: () {
              PreLoader.show(context);
              ScaffoldMessenger.of(context).clearSnackBars();

              widget.controller.setUserHeroStructuralValues(widget.hero, widget.attribute)
                .then((response) {
                  Navigator.of(context).pop();
                })
                .catchError((error) {
                  SnackBar snackBar = MainSnackBar(
                    content: Text(AppLocalizations.of(context).service_error),
                  );

                  if (error.runtimeType == ServerError) {
                    snackBar = ServerError.toSnackBar(error);
                  }

                  ScaffoldMessenger.of(context).showSnackBar(snackBar);
                })
                .whenComplete(() {
                  PreLoader.hide(context);
                });
            },
            icon: Icon(Icons.done),
          ),
        ],
      ),
      body: Column(
        mainAxisSize: MainAxisSize.min,
        children: [
          Container(
            padding: EdgeInsets.all(10.0),
            child: TextField(
              controller: controller,
              onChanged: (query) {
                setState(() {
                  _query = query;
                });
              },
              decoration: InputDecoration(
                labelText: AppLocalizations.of(context).search,
                prefixIcon: Icon(Icons.search),
              ),
            ),
          ),
          Expanded(
            child: SingleChildScrollView(
              child: Container(
                child: FutureBuilder(
                  future: widget.controller.getValues(widget.attribute),
                  builder: (BuildContext context, AsyncSnapshot<List<StructuralValue>> snapshot) {
                    if (!snapshot.hasData && snapshot.connectionState == ConnectionState.waiting) {
                      return Center(
                        child: PreLoaderWidget(),
                      );
                    } else if (snapshot.hasError) {
                      return Text('Error');
                    }

                    return DataTable(
                      columns: [
                        DataColumn(label: Text(AppLocalizations.of(context).name)),
                      ],
                      rows: _getDataRowsFromValues(snapshot.data),
                    );
                  },
                ),
              ),
            ),
          ),
        ],
      ),
    );
  }
}
