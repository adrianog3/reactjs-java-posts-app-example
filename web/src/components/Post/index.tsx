import { Avatar, Divider, Paper } from "@material-ui/core";
import { ThumbUp } from "@material-ui/icons";
import React from "react";
import { IPost } from "../../services/post";
import { Styles } from "./styles";
import { formatDate } from "../../utils/date_utils";

function Post(props: React.PropsWithChildren<IPost>) {
  return (
    <Styles>
      <Paper className="post" elevation={2}>
        <div className="header">
          <Avatar src="#" />
          <div className="card-title">
            <span className="title">{props.author}</span>
            <span className="subtitle">{formatDate(props.createdAt)}</span>
          </div>
        </div>
        <Divider style={{ marginTop: "1.01rem" }} />
        <div>
          <h2>{props.title}</h2>
          <p>{props.text}</p>
        </div>
        <div className="footer">
          <ThumbUp fontSize="small" />
          <span>{props.votesCount}</span>
        </div>
      </Paper>
    </Styles>
  );
}

export default Post;
