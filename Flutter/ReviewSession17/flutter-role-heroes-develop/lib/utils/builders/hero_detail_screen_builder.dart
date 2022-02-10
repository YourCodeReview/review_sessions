import 'package:flutter/material.dart';
import 'package:role_heroes/controllers/user_hero.dart';
import 'package:role_heroes/models/attribute.dart';
import 'package:role_heroes/models/category/category.dart';
import 'package:role_heroes/models/characteristic/characteristic.dart';
import 'package:role_heroes/modules/structural_attribute/models/structural_attribute.dart';
import 'package:role_heroes/modules/heroes/models/user_hero.dart';
import 'package:role_heroes/modules/heroes/widgets/field.dart';
import 'package:role_heroes/modules/structural_attribute/widgets/structural_attribute.dart';
import 'package:role_heroes/utils/value_types.dart';
import 'package:role_heroes/widgets/category_tab.dart';
import 'package:flutter_gen/gen_l10n/app_localizations.dart';

abstract class IHeroDetailScreenBuilder {
  void reset();
  void build(BuildContext context, UserHero hero, IUserHeroController controller);
  Map getResult();
}

class HeroDetailScreenBuilder extends IHeroDetailScreenBuilder {
  Map<CategoryTab, List<Widget>> _mapCategoryToWidgets;

  @override
  void reset() {
    this._mapCategoryToWidgets = new Map<CategoryTab, List<Widget>>();
  }

  @override
  void build(BuildContext context, UserHero hero, IUserHeroController controller) {
    this.buildMainFieldsCategory(context, hero, controller);
    this.buildCharacteristicsCategory(context, hero, controller);
    this.buildAttributes(context, hero, controller);
    this.buildStructuralAttributes(context, hero, controller);
  }

  @override
  Map<CategoryTab, Widget> getResult() {
    return this
      ._mapCategoryToWidgets
      .map<CategoryTab, Widget>(
        (CategoryTab category, List<Widget> widgets) => MapEntry(
          category,
          ListView.builder(
            itemCount: widgets.length,
            itemBuilder: (BuildContext context, int index) => widgets.elementAt(index),
          ),
        )
    );
  }

  void buildMainFieldsCategory(BuildContext context, UserHero hero, IUserHeroController controller) {
    final CategoryTab category = CategoryTab(
      category: Category(
        id: -1,
        name: AppLocalizations.of(context).hero_detail_main_fields,
      ),
    );

    this._saveWidgetToCategory(
      category,
      Field(
        name: AppLocalizations.of(context).hero_detail_main_fields_name,
        type: StringType(),
        value: hero.name,
        setValue: (value) => controller.updateData(
          hero,
          {'name': value},
        ),
      ),
    );

    this._saveWidgetToCategory(
      category,
      Field(
        name: AppLocalizations.of(context).hero_detail_main_fields_note,
        type: StringType(),
        value: hero.note,
        setValue: (value) => controller.updateData(
          hero,
          {'note': value},
        )
      ),
    );
  }

  void buildCharacteristicsCategory(BuildContext context, UserHero hero, IUserHeroController controller) {
    final CategoryTab category = CategoryTab(
      category: Category(
        id: -2,
        name: AppLocalizations.of(context).hero_detail_characteristics,
      ),
    );

    for (final Characteristic characteristic in hero.characteristics) {
      this._saveWidgetToCategory(
        category,
        Field(
          name: characteristic.name,
          type: IntType(),
          value: characteristic.value,
          setValue: (value) {
            characteristic.value = value;
            return controller.updateCharacteristic(hero, characteristic);
          },
        ),
      );
    }
  }

  void buildAttributes(BuildContext context, UserHero hero, IUserHeroController controller) {
    Map<Category, List<Attribute>> categoriesAttributes = Map.fromIterable(
        hero.attributes,
        key: (attribute) => attribute.category,
        value: (attribute) => []
    );
    hero.attributes.forEach((Attribute attribute) {
      categoriesAttributes[attribute.category].add(attribute);
    });
    categoriesAttributes.forEach((Category category, List<Attribute> attributes) {
      final CategoryTab categoryTab = CategoryTab(category: category);
      for (final Attribute attribute in attributes) {
        this._saveWidgetToCategory(
          categoryTab,
          Field(
            name: attribute.name,
            type: attribute.type,
            value: attribute.value,
            setValue: (value) {
              attribute.value = value;
              return controller.updateAttribute(hero, attribute);
            },
          ),
        );
      }
    });
  }

  void buildStructuralAttributes(BuildContext context, UserHero hero, IUserHeroController controller) {
    Map<Category, List<StructuralAttribute>> categoriesAttributes = Map.fromIterable(
      hero.structuralAttributes,
      key: (structuralAttribute) => structuralAttribute.category,
      value: (structuralAttribute) => [],
    );

    hero.structuralAttributes.forEach((StructuralAttribute structuralAttribute) {
      categoriesAttributes[structuralAttribute.category].add(
        structuralAttribute
      );
    });

    categoriesAttributes.forEach((Category category, List<StructuralAttribute> attributes) {
      final CategoryTab categoryTab = CategoryTab(category: category);

      for (final StructuralAttribute attribute in attributes) {
        this._saveWidgetToCategory(
          categoryTab,
          StructuralAttributeWidget(hero: hero, attribute: attribute),
        );
      }
    });
  }

  void _saveWidgetToCategory(CategoryTab categoryTab, Widget widget) {
    bool containKey = false;
    CategoryTab categoryTabKey = categoryTab;
    for (final CategoryTab key in this._mapCategoryToWidgets.keys) {
      if (key.category.id == categoryTab.category.id) {
        containKey = true;
        categoryTabKey = key;
      }
    }

    if (!containKey) {
      this._mapCategoryToWidgets[categoryTabKey] = [];
    }

    this._mapCategoryToWidgets[categoryTabKey].add(widget);
  }
}
