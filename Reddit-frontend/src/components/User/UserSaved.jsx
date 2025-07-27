import { useEffect, useState } from "react";
import { contentToPost, contentToComment } from "../../util/contentMapper";
import { usePagination } from "../../hooks/usePagination";
import EmptyContent from "../Common/EmptyContent";
import savedContentService from "../../services/savedContents";
import Pagination from "../Common/Pagination";
import Post from "../Post/Post";
import Comment from "../Comment/Comment";

const UserSaved = ({ profile }) => {
  const [contents, setContents] = useState([]);
  const {
    page,
    goToNextPage,
    goToPreviousPage,
    isEmpty,
    setIsEmpty,
    isFirst,
    setIsFirst,
    isLast,
    setIsLast,
  } = usePagination();

  useEffect(() => {
    const getSavedContents = async () => {
      try {
        const savedContentPage =
          await savedContentService.getSavedContentByUserId(profile.id, {
            page,
          });

        setIsEmpty(savedContentPage.empty);
        setContents(
          savedContentPage.content.map(
            (savedContent) => savedContent.contentDto
          )
        );
        setIsFirst(savedContentPage.first);
        setIsLast(savedContentPage.last);
      } catch (error) {
        console.log(error);
      }
    };

    getSavedContents();
  }, [profile.id, page]);

  const handlePageChange = (change) => {
    setPage(page + change);
  };

  if (isEmpty) {
    return (
      <>
        <EmptyContent text={`u/${profile.username} hasn't saved yet`} />
      </>
    );
  }

  return (
    <div>
      <ul>
        {contents.map((content) => {
          if (content.contentType === "POST") {
            const post = contentToPost(content);
            return (
              <li key={post.id}>
                <Post post={post} deletePost={null} />
              </li>
            );
          }

          const comment = contentToComment(content);

          return (
            <li key={comment.id}>
              <Comment comment={comment} deleteComment={null} />
            </li>
          );
        })}
      </ul>

      <Pagination
        goToNextPage={goToNextPage}
        goToPreviousPage={goToPreviousPage}
        isFirst={isFirst}
        isLast={isLast}
      />
    </div>
  );
};

export default UserSaved;
