const Pagination = ({ goToNextPage, goToPreviousPage, isFirst, isLast }) => {
  return (
    <div className="my-8 flex justify-start space-x-2">
      {!isFirst && (
        <button
          className={`w-24 p-2 bg-gray-800 hover:bg-black text-gray-200 border rounded-3xl`}
          onClick={goToPreviousPage}
        >
          Prev
        </button>
      )}

      {!isLast && (
        <button
          className={`w-24 p-2 bg-gray-800 hover:bg-black text-gray-200 border rounded-3xl`}
          onClick={goToNextPage}
        >
          Next
        </button>
      )}
    </div>
  );
};

export default Pagination;
