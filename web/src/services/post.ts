import api from "./api";

export interface IPostsResponse {
  content: IPost[];
  message?: string;
}

export interface IPost {
  id: number;
  author: string;
  title: string;
  text: string;
  createdAt: string;
  votesCount: number;
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

export async function findPosts(): Promise<IPostsResponse> {
  try {
    const response = await api.get<IPostsResponse>("/api/v1/posts");

    return response.data && response.data.content ? response.data : { content: [] };
  } catch (err) {
    console.log(err);
    return { content: [], message: "Falha ao buscar postagens" };
  }
}
