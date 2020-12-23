import React, { useEffect, useState } from "react";
import Post from "../../components/Post";
import { IPost, findPosts } from "../../services/post";
import { useSnackbar } from "notistack";

function Home() {
  const { enqueueSnackbar } = useSnackbar();
  const [posts, setPosts] = useState<IPost[]>();

  useEffect(() => {
    findPosts().then((response) => {
      if (response.message) {
        enqueueSnackbar(response.message, { variant: "error" });
      } else {
        setPosts(response.content);
      }
    });
  }, [enqueueSnackbar]);

  return (
    <>
      {posts &&
        posts.map((post) => {
          return <Post key={post.id} {...post} />;
        })}
    </>
  );
}

export default Home;
