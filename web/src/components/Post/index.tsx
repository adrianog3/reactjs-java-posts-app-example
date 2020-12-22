import React from "react";
import { Paper } from "@material-ui/core";
import { Divider } from "@material-ui/core";
import CardHeader from "./CardHeader";

import { StylesWrapper } from "./styles";
import CardFooter from "./CardFooter";

function Post() {
  return (
    <StylesWrapper>
      <Paper className="post" elevation={2}>
        <CardHeader />
        <Divider style={{ marginTop: "1.01rem" }} />
        <div>
          <h2>Kubernetes</h2>
          <p>
            Kubernetes (comumente estilizado como K8s) é um sistema de
            orquestração de contêineres " + "open-source que automatiza a
            implantação, o dimensionamento e a gestão de aplicações em " +
            "contêineres. Ele foi originalmente projetado pelo Google e agora é
            mantido pela " + "Cloud Native Computing Foundation. Ele funciona
            com uma variedade de ferramentas de " + "conteinerização, incluindo
            Docker.
          </p>
        </div>
        <CardFooter />
      </Paper>
    </StylesWrapper>
  );
}

export default Post;
