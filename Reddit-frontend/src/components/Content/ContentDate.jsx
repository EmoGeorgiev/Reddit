const ContentDate = ({ created }) => {
  const date = created.split("T")[0];

  return <div className="font-light">{date}</div>;
};

export default ContentDate;
