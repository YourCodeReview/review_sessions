import React from "react";
import Card from "./components/Card/index";

const PRODUCT = [
  {
    id: 1,
    img: require("./assets/image/img-product_1.jpg"),
    name: "Шампунь",
    desc: `Lorem ipsum dolor sit amet, consectetur adipiscing elit,
     sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. 
    Ut enim ad minim veniam, quis nostrud exercitation ullamco`,
  },
  {
    id: 2,
    img: require("./assets/image/img-product_2.jpg"),
    name: "Шампунь",
    desc: `Lorem ipsum dolor sit amet, consectetur adipiscing elit,
     sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. 
    Ut enim ad minim veniam, quis nostrud exercitation ullamco`,
  },
  {
    id: 3,
    img: require("./assets/image/img-product_3.jpg"),
    name: "Шампунь",
    desc: `Lorem ipsum dolor sit amet, consectetur adipiscing elit,
     sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. 
    Ut enim ad minim veniam, quis nostrud exercitation ullamco`,
  },
];

const App = () => {
  return PRODUCT.map((item) => <Card key={item.id} {...item} />);
};

export default App;
