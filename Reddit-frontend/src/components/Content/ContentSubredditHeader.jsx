import { Link } from "react-router-dom";
import subredditIcon from "../../assets/subreddit-icon.svg";

const ContentSubredditHeader = ({ subreddit }) => {
  return (
    <Link to={`/r/${subreddit.title}`}>
      <div className="flex space-x-1.5 items-center">
        <img className="w-10 h-10" src={subredditIcon} alt="subreddit" />
        <span className="font-semibold">r/{subreddit.title}</span>
      </div>
    </Link>
  );
};

export default ContentSubredditHeader;
