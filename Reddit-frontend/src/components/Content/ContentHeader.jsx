import { useLocation } from "react-router-dom";
import ContentDate from "./ContentDate";
import ContentSubredditHeader from "./ContentSubredditHeader";
import ContentUserHeader from "./ContentUserHeader";

const ContentHeader = ({ user, subreddit, created }) => {
  const location = useLocation();

  const showUser = () => {
    const subredditPath = `/r/${subreddit.title}`.toLowerCase();
    const currentPath = location.pathname.toLowerCase();

    return currentPath.includes(subredditPath);
  };

  return (
    <div className="flex justify-between items-center">
      {showUser() ? (
        <ContentUserHeader user={user} />
      ) : (
        <ContentSubredditHeader subreddit={subreddit} />
      )}
      <ContentDate created={created} />
    </div>
  );
};

export default ContentHeader;
