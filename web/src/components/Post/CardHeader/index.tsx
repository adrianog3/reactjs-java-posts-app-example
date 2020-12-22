import React from "react";

import { Styles } from "./styles";

function CardHeader() {
  return (
    <Styles>
      <div className="avatar"></div>
      <div className="card-title">
        <span className="title">Adriano Souza Arruda</span>
        <span className="subtitle">23/10/2020 às 10:35</span>
      </div>
    </Styles>
  );
}

export default CardHeader;
