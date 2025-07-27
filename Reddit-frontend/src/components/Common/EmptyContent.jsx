import thinkingIcon from "../../assets/thinking-icon.svg";

const EmptyContent = ({ text }) => {
  return (
    <div className="mt-4 flex flex-col justify-center items-center">
      <img src={thinkingIcon} alt="empty" />

      <h1 className="page-header font-semibold">{text}</h1>
    </div>
  );
};

export default EmptyContent;
