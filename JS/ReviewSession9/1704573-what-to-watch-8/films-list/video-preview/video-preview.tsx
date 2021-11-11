import React, { useEffect, useRef } from 'react';

type VideoPreviewProps = {
  poster: string,
  src: string,
  isPlaying: boolean,
}

const PREVIEW_DELAY = 1000;

function VideoPreview(props: VideoPreviewProps): JSX.Element {
  const { src, isPlaying, poster } = props;

  const videoRef = useRef<HTMLVideoElement | null>(null);

  useEffect(() => {
    let timeoutId: ReturnType<typeof setTimeout>;

    if (videoRef && videoRef.current) {
      const player = videoRef.current;

      if (isPlaying) {
        timeoutId = setTimeout(() => {
          player.play();
        }, PREVIEW_DELAY);
      } else {
        player.load();
      }
    } else {
      return;
    }

    return () => {
      clearTimeout(timeoutId);
    };
  }, [isPlaying, videoRef]);

  return (
    <video
      ref={videoRef}
      src={src}
      poster={poster}
      width="280"
      height="175"
      muted
    />
  );
}

export default VideoPreview;
