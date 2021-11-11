import React, { memo } from 'react';
import { Link } from 'react-router-dom';
import { Film } from '../../../types/film';
import VideoPreview from '../video-preview/video-preview';

type FilmCardProps = {
  film: Film,
  onMouseOver: (evt: React.MouseEvent) => void,
  onMouseLeave: (evt: React.MouseEvent) => void,
  isActive: boolean,
}

function FilmCard(props: FilmCardProps): JSX.Element {
  const { previewImage, name, id, previewVideoLink } = props.film;
  const { onMouseOver, onMouseLeave, isActive } = props;

  return (
    <article
      className="small-film-card catalog__films-card"
      onMouseOver={onMouseOver}
      onMouseLeave={onMouseLeave}
      id={id}
    >
      <div className="small-film-card__image">
        <VideoPreview
          poster={previewImage}
          src={previewVideoLink}
          isPlaying={isActive}
        />
      </div>
      <h3 className="small-film-card__title">
        <Link className="small-film-card__link" to={`/films/${id}`}>{name}</Link>
      </h3>
    </article>
  );
}

export default memo(FilmCard);
