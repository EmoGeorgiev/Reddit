import { SizeOptions } from "../../util/SizeOptions";

const CommentSectionSize = ({ length, totalElements, setSize }) => {
  return (
    <>
      {length < SizeOptions.Default ? (
        <div className="font-semibold">
          {`All ${length} ${length === 1 ? "comment" : "comments"}`}
        </div>
      ) : (
        <div className="flex space-x-4 font-semibold">
          <div>{`Top ${length} comments`}</div>
          <button onClick={() => setSize(totalElements)}>
            {`Show ${totalElements}`}
          </button>
        </div>
      )}
    </>
  );
};

export default CommentSectionSize;
