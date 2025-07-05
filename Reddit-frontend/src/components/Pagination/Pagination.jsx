const Pagination = ({ handlePageChange, isFirst, isLast }) => {
    return (
        <div>
            <button disabled={isFirst} onClick={() => handlePageChange(-1)}>
                Prev
            </button>

            <button disabled={isLast} onClick={() => handlePageChange(1)}>
                Next
            </button>
        </div>
    )
}

export default Pagination