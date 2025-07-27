import subredditIcon from "../../assets/subreddit-icon.svg";

const SubredditHeader = ({ title }) => {
  return (
    <div className="flex items-center space-x-2">
      <img className="w-20 h-20" src={subredditIcon} alt="subreddit" />
      <h1 className="text-4xl page-header">r/{title}</h1>
    </div>
  );
};

export default SubredditHeader;
