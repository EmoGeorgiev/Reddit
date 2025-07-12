import { useEffect, useState } from 'react'
import Pagination from '../Common/Pagination'
import ContentUserHeader from '../Content/ContentUserHeader'

const UserList = ({ query, getUsers }) => {
    const [users, setUsers] = useState([])
    const [page, setPage] = useState(0)
    const [isFirst, setIsFirst] = useState(true)
    const [isLast, setIsLast] = useState(true)

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

            <Pagination handlePageChange={handlePageChange} isFirst={isFirst} isLast={isLast} />
        </div>
    )
}

export default UserList