import { useRef, useState } from 'react'
import searchIcon from '../../assets/search-icon.svg'
import closIcon from '../../assets/close-icon.svg'

const Search = () => {
    const [query, setQuery] = useState('')
    const inputRef = useRef()

    const handleQueryChange = (e) => {
        setQuery(e.target.value)
    }

    const clearQuery = () => {
        setQuery('')
    }

    return (
        <div className='w-full flex items-center relative group'>
            <button className='absolute left-3 flex items-center' onClick={() => inputRef.current?.focus()}>
                <img className='w-5 h-5' src={searchIcon} alt='search' />
            </button>
            
            <input
                className='w-full px-10 p-2 font-light bg-gray-200 hover:bg-gray-300 focus-item rounded-full' 
                ref={inputRef}
                type='text'
                name='query'
                value={query}
                placeholder='Search Reddit'
                onChange={handleQueryChange}
            />

            {query.length !== 0 && <button className='absolute right-3' onClick={clearQuery}>
                <img className='w-4 h-4' src={closIcon} alt='clear' />
            </button>}
        </div>
    )
}

export default Search