import { useState } from 'react'

export const usePagination = () => {
    const [page, setPage] = useState(0)
    const [isEmpty, setIsEmpty] = useState(false)
    const [isFirst, setIsFirst] = useState(true)
    const [isLast, setIsLast] = useState(true)
    
    const goToNextPage = () => {
        setPage(page + 1)
    }

    const goToPreviousPage = () => {
        setPage(page - 1)
    }

    return {
        page,
        goToNextPage,
        goToPreviousPage,
        isEmpty,
        setIsEmpty,
        isFirst,
        setIsFirst,
        isLast,
        setIsLast
    }
}