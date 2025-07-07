const Pagination = ({ handlePageChange, isFirst, isLast }) => {
    return (
        <div className='my-8 flex justify-start space-x-2'>
            {!isFirst && <button className={`w-24 p-2 bg-gray-800 hover:bg-black text-gray-200 border rounded-3xl`} onClick={() => handlePageChange(-1)}>
                Prev
            </button>}

            {!isLast && <button className={`w-24 p-2 bg-gray-800 hover:bg-black text-gray-200 border rounded-3xl`} onClick={() => handlePageChange(1)}>
                Next
            </button>}
        </div>
    )
}

export default Pagination