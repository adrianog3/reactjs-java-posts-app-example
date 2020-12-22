import styled from "styled-components";

export const StylesWrapper = styled.span`
  .post {
    padding: 1rem 1rem 1rem 1rem;
    margin-bottom: 0.8rem;
  }

  .post h1,
  p {
    text-align: left;
  }

  .upvote {
    display: flex;
    align-items: center;
  }

  .upvote span {
    padding: 0.5rem;
  }

  .upvote button {
    opacity: 100;
    background-color: transparent;
    border: none;
    outline: none;
    cursor: pointer;
  }
`;
