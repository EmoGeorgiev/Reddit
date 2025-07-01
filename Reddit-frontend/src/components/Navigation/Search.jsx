import { useState } from 'react'

const Search = () => {
    const [query, setQuery] = useState('')

    const handleQueryChange = (e) => {
        setQuery(e.target.value)
    }

    const clearQuery = () => {
        setQuery('')
    }

    return (
        <div className='w-full flex items-center relative'>
            <input
                className='w-full pr-14 p-2 font-light bg-gray-200 hover:bg-gray-300 focus-item rounded-full' 
                type='text'
                name='query'
                value={query}
                placeholder='Search Reddit'
                onChange={handleQueryChange}
            />

            {query.length !== 0 && <button className='absolute right-3' onClick={clearQuery}>
                Clear
            </button>}
        </div>
    )
}

export default Search