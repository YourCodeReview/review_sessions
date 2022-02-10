import React from "react";

import styleComponent from "./style.module.css";

import CheckBox from "./Checkbox/checkbox";

const SIZE = [
  {
    id: 1,
    volume: "100 мл",
  },
  {
    id: 2,
    volume: "200 мл",
  },
  {
    id: 3,
    volume: "300 мл",
  },
];
const COLORS = ["Цвет", "желтый", "красный", "синий"];
const price = 200;

const Card = ({ img, name, desc }) => {
  return (
    <React.Fragment>
      <div className={styleComponent.container}>
        <div className={styleComponent.card}>
          {/* box-image */}
          <div className={styleComponent.card__boxImage}>
            <img
              className={styleComponent.card__iamge}
              src={img}
              alt={name}
            ></img>
          </div>
          {/* box-text */}
          <div className={styleComponent.card__textBox}>
            <span className={styleComponent.card__productName}>{name}</span>
            <p className={styleComponent.card__productDescription}>{desc}</p>
          </div>
          {/* box-navigator */}
          <div className={styleComponent.card__boxNavigator}>
            {/* custom select */}
            <div className={styleComponent.card__boxSelect}>
              <div className={styleComponent.card__colorSelect}>
                {COLORS.map((item) => <span>{item}</span>)}
              </div>
              <p className={styleComponent.card__price}>
                <span className={styleComponent.card__priceNumber}>
                  {price}
                </span>{" "}
                грн
              </p>
            </div>
            {/* checkbox */}
            {SIZE.map((volume) => (
              <CheckBox key={volume.id} {...volume} />
            ))}
            {/* count prouduct and buy */}
            <div className={styleComponent.card__boxPay}>
              <div className={styleComponent.card__countProduct}>1</div>
              <a href="#" className={styleComponent.card__btnProduct}>
                Купить
              </a>
            </div>
          </div>
        </div>
      </div>
    </React.Fragment>
  );
};

export default Card;
