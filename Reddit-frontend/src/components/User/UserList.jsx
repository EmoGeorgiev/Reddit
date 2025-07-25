import { useEffect, useState } from 'react'
import { usePagination } from '../../hooks/usePagination'
import Pagination from '../Common/Pagination'
import ContentUserHeader from '../Content/ContentUserHeader'

const UserList = ({ query, getUsers }) => {
    const [users, setUsers] = useState([])
    const { 
        page,
        goToNextPage,
        goToPreviousPage,
        isFirst,
        setIsFirst,
        isLast,
        setIsLast
    } = usePagination()

    useEffect(() => {
        const getUserPage = async () => {
            try {
                const userPage = await getUsers(query, { page })

                setUsers(userPage.content)
                setIsFirst(userPage.first)
                setIsLast(userPage.last)
            } catch (error) {
                console.log(error)
            }
        }

        getUserPage()
    }, [query, page])

    const handlePageChange = (change) => {
        setPage(page + change)
    }

    return (
        <div>
            <ul>
                {users.map(user => 
                        <li className='border-b border-gray-300' key={user.id}>
                            <div className='p-4 hover:bg-gray-100 rounded-2xl overflow-clip'>
                                <ContentUserHeader user={user} />
                            </div>
                        </li>)}
            </ul>

            <Pagination goToNextPage={goToNextPage} goToPreviousPage={goToPreviousPage} isFirst={isFirst} isLast={isLast} />
        </div>
    )
}

export default UserList