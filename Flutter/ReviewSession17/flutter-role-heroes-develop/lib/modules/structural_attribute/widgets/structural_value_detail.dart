import 'package:flutter/material.dart';
import 'package:role_heroes/modules/structural_attribute/models/structural_field.dart';
import 'package:role_heroes/modules/structural_attribute/models/structural_value.dart';

class StructuralValueDetail extends StatelessWidget {
  final List<StructuralField> fields;
  final StructuralValue value;

  const StructuralValueDetail({
    Key key,
    @required this.fields,
    @required this.value,
  }) : super(key: key);

  List<DataRow> _getRows() {
    List<DataRow> result = [];
    for (int i = 0; i < value.fields.length; i++) {
      result.add(DataRow(cells: [
        DataCell(Text(fields.elementAt(i).name)),
        DataCell(Text(value.fields.elementAt(i).value.toString())),
      ]));
    }
    return result;
  }

  @override
  Widget build(BuildContext context) {
    List<Widget> content = [];
    if (value.description != null)
      content.add(Text(value.description, style: Theme.of(context).textTheme.bodyText2));

    if (value.fields.length > 0) {
      content.add(DataTable(
        columnSpacing: 10.0,
        columns: [
          DataColumn(label: Text('Название')),
          DataColumn(label: Text('Значение')),
        ],
        rows: _getRows(),
      ));
    }

    return AlertDialog(
      title: Text(this.value.name),
      scrollable: true,
      content: Column(
        children: content,
      ),
    );
  }
}
