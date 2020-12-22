import styled from "styled-components";

export const Styles = styled.div`
  & {
    display: flex;
    align-items: center;
    justify-content: left;
  }

  & span {
    padding-left: 0.8rem;
  }

  .avatar {
    height: 2.5rem;
    width: 2.5rem;
    background-color: #bbb;
    border-radius: 50%;
  }

  .card-title {
    display: flex;
    flex-direction: column;
  }

  .card-title .title {
    padding-bottom: 0.25rem;
    font-size: 0.95rem;
  }

  .card-title .subtitle {
    color: #757575;
    font-size: 0.9rem;
  }
`;
