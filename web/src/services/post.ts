import api from "./api";

export interface IPost {
  author: string;
  title: string;
  text: string;
}

export async function savePost(post: IPost): Promise<String> {
  if (!post) {
    return "Dados da postagem não informados";
  }

  if (!post.author) {
    return "O autor da postagem é um campo obrigatório";
  }

  if (!post.title) {
    return "O título da postagem é um campo obrigatório";
  }

  if (!post.text) {
    return "O texto da postagem é um campo obrigatório";
  }

  try {
    await api.post("/api/v1/posts", post);
    return "";
  } catch (err) {
    if (err && err.response && err.response.data) {
      console.log(err.response.data);
    } else {
      console.log(err);
    }

    return "Falha ao criar postagem";
  }
}
