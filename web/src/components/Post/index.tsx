import React, { useState } from "react";
import { Avatar, Divider, Paper } from "@material-ui/core";
import { ThumbUp } from "@material-ui/icons";
import { IPost } from "../../services/post";
import { Styles } from "./styles";
import { formatDate } from "../../utils/date_utils";
import { useSnackbar } from "notistack";
import { upvotePost } from "../../services/post";

function Post(props: React.PropsWithChildren<IPost>) {
  const [votesCount, setVotesCount] = useState(props.votesCount);
  const { enqueueSnackbar } = useSnackbar();

  function handleUpvoteClick() {
    upvotePost(props.id).then((message) => {
      if (message) {
        enqueueSnackbar(message, { variant: "error" });
      } else {
        setVotesCount((previous) => previous + 1);
      }
    });
  }

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
          <button onClick={handleUpvoteClick} className="btn-upvote">
            <ThumbUp fontSize="small" />
          </button>
          <span>{votesCount ? votesCount : 0}</span>
        </div>
      </Paper>
    </Styles>
  );
}

export default Post;
