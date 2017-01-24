import React from 'react'
import { connect } from 'react-redux'
import * as editor from '../../../shared/modules/history/historyDuck'
import * as favorite from '../../../shared/modules/favorites/favoritesDuck'
import {FavoriteItem} from 'nbnmui/buttons'

function extractNameFromCommand (input) {
  if (!input) {
    return ''
  }

  let firstRow = input.split('\n')[0]
  if (firstRow.indexOf('//') === 0) {
    return firstRow.substr(2).trim()
  } else {
    return input.trim()
  }
}

export const Favorite = ({id, content, onItemClick = () => {}, removeClick = () => {}}) => {
  const name = extractNameFromCommand(content)
  return (
    <FavoriteItem
      style={{color: 'white'}}
      className='favorite'
      primaryText={name}
      onClick={() => onItemClick(content)}
      removeClick={() => removeClick(id)}
    />
  )
}

const mapDispatchToProps = (dispatch) => {
  return {
    onItemClick: (cmd) => {
      dispatch(editor.setContent(cmd))
    },
    removeClick: (id) => {
      dispatch(favorite.removeFavorite(id))
    }
  }
}

export default connect(null, mapDispatchToProps)(Favorite)